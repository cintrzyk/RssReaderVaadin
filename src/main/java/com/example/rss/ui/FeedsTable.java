package com.example.rss.ui;

import com.example.rss.VaadinApp;
import com.example.rss.domain.Feed;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

public class FeedsTable extends VerticalLayout{
	private static final long serialVersionUID = 1L;
	
	Table feeds_table;
	VaadinApp vaadin_app;
	public FeedsTable(BeanItemContainer<Feed> container, final VaadinApp vaadin_app) {
		feeds_table = new Table("Feeds List", container); 
		this.vaadin_app = vaadin_app;
		addComponent(feeds_table);
		
		feeds_table.setSelectable(true);
		feeds_table.setImmediate(true);
	
		feeds_table.setWidth("100%");
		feeds_table.setColumnExpandRatio("title",3);
		feeds_table.setColumnExpandRatio("url",2);
		feeds_table.setColumnExpandRatio("date",1);
		feeds_table.setVisibleColumns(new String[] {"title", "url", "date"});
		
		feeds_table.addListener(new ItemClickListener() {
			private static final long serialVersionUID = 1L;
			@Override
			public void itemClick(ItemClickEvent event) {
				
				//vaadin_app.getStorageService().addFeeds(event.getItem().getItemProperty("description").toString());
				getWindow().addWindow(new DescriptionPopUp(
						event.getItem().getItemProperty("title").toString(), 
						event.getItem().getItemProperty("description").toString(),
						event.getItem().getItemProperty("url").toString()).createWindow()
						);
			}
		});
		
	}
}
