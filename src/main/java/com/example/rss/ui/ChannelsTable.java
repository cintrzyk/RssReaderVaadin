package com.example.rss.ui;

import com.example.rss.VaadinApp;
import com.example.rss.domain.Channel;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

public class ChannelsTable extends VerticalLayout{
	private static final long serialVersionUID = 1L;
	
	Table channels_table; 
	VaadinApp vaadin_app;
	public ChannelsTable(BeanItemContainer<Channel> container, final VaadinApp vaadin_app) {
		this.vaadin_app = vaadin_app;
		channels_table = new Table("Channels list", container); 
		addComponent(channels_table);
		
		channels_table.setSelectable(true);
        channels_table.setMultiSelect(true);
		channels_table.setImmediate(true);
		channels_table.setWriteThrough(false);

		channels_table.setWidth("100%");
		channels_table.setColumnExpandRatio("name",2);
		channels_table.setColumnExpandRatio("link",1);
		channels_table.setColumnHeaders(new String[] { "Link", "Name"});
		
		channels_table.addListener(new Table.ValueChangeListener() {
			private static final long serialVersionUID = 1L;

			public void valueChange(ValueChangeEvent event) {
               vaadin_app.updateFeedsTable();
            }
        });
		
		channels_table.addListener(new ItemClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void itemClick(ItemClickEvent event) {
				vaadin_app.getStorageService().removeAllFeeds();
				vaadin_app.getStorageService().addFeeds(event.getItem().getItemProperty("link").toString());
				vaadin_app.updateFeedsTable();
			}
		});

        final Button editButton = new Button("Edit");
        addComponent(editButton);
        editButton.addListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;
			@Override 
        	public void buttonClick(ClickEvent event) {
                channels_table.setEditable(!channels_table.isEditable());
                editButton.setCaption((channels_table.isEditable() ? "Save" : "Edit"));
            }
        });
        setComponentAlignment(editButton, Alignment.TOP_RIGHT);
		
	}

}
