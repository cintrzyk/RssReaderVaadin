package com.example.rss;

import java.util.ArrayList;
import java.util.List;

import com.example.rss.domain.Channel;
import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Field;
import com.vaadin.ui.Form;
import com.vaadin.ui.FormFieldFactory;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;

public class ChannelWindowFormFactory extends Form implements FormFieldFactory {
	private static final long serialVersionUID = 1L;
	List<String> visible_fields = new ArrayList<String>();
	Button add_button = new Button("Add");
	Button close_button = new Button("Close");
	Window modal_window;
	BeanItem<Channel> channelBeanItem;
	VaadinApp vaadin_app;
	
	public ChannelWindowFormFactory(BeanItem<Channel> channelBeanItem, VaadinApp vaadin_app) {
		this.vaadin_app = vaadin_app;

		GridLayout gl = new GridLayout(2, 4);
		gl.setSpacing(true);
		setLayout(gl);

		this.channelBeanItem = channelBeanItem;
		visible_fields.add("name");
		visible_fields.add("link");

		setImmediate(true);
		setValidationVisibleOnCommit(true);

		// 1.
		setFormFieldFactory(this);
		// 2.
		setItemDataSource(channelBeanItem);
		// 3.
		setVisibleItemProperties(visible_fields);

		close_button.addListener(ClickEvent.class, this, "closeWindowAction");
		add_button.addListener(ClickEvent.class, this, "addChannelAction");

		gl.addComponent(add_button, 0, 3);
		gl.addComponent(close_button, 1, 3);
	}

	@Override
	protected void attachField(Object propertyId, Field field) {
		String property = (String) propertyId;

		GridLayout gl = (GridLayout) getLayout();

		if ("name".equals(property)) {
			gl.addComponent(field, 0, 0, 1, 0);

		} else if ("link".equals(property)) {
			gl.addComponent(field, 0, 1, 1, 1);
		}
	}
	
	@Override
	public Field createField(Item item, Object propertyId, Component uiContext) {
		String field = (String) propertyId;

		if ("name".equals(field)) {
			TextField name_text_field = new TextField("Name");
			name_text_field.setRequired(true);
			name_text_field.focus();
			return name_text_field;

		} else if ("link".equals(field)) {
			TextField link_text_field = new TextField("Link");
			link_text_field.addValidator(new RegexpValidator("\\bhttps?://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]",
                    "Not valid url."));
			link_text_field.setRequired(true);
			return link_text_field;
		}
		return null;
	}

	public Window createWindow() {
		modal_window = new Window();
		modal_window.setModal(true);
		modal_window.setClosable(false);
		modal_window.addComponent(this);

		((VerticalLayout) modal_window.getContent()).setSizeUndefined();
		((VerticalLayout) modal_window.getContent()).setMargin(true);
		((VerticalLayout) modal_window.getContent()).setSpacing(true);

		return modal_window;
	}

	public void closeWindowAction(ClickEvent event) {
		closeFormWindow();
		vaadin_app.updateChannelsTable();
	}

	public void addChannelAction(ClickEvent event) {
		commit();
		Channel channel = channelBeanItem.getBean();
		vaadin_app.getStorageService().addChannel(channel);
		closeFormWindow();
		vaadin_app.updateChannelsTable();
	}

	private void closeFormWindow() {
		Window main_window = modal_window.getParent();
		main_window.removeWindow(modal_window);
	}
}
