package com.example.rss;

import com.example.rss.domain.Channel;
import com.example.rss.domain.Feed;
import com.example.rss.ui.ChannelsTable;
import com.example.rss.ui.FeedsTable;
import com.example.rss.service.StorageService;
import com.vaadin.Application;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.SplitPanel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
//import com.vaadin.ui.themes.Reindeer;
import com.vaadin.ui.Button.ClickEvent;

@SuppressWarnings("deprecation")
public class VaadinApp extends Application {

	private static final long serialVersionUID = 1L;
    
	private BeanItemContainer<Channel> channelsContainer = new BeanItemContainer<Channel>(Channel.class);
	private BeanItemContainer<Feed> feedsContainer = new BeanItemContainer<Feed>(Feed.class);

	private Button new_channel = new Button("Add channel");
	private Window channel_form_window;

    private HorizontalSplitPanel horizontalSplit = new HorizontalSplitPanel();
    
    private ChannelsTable channels_table = new ChannelsTable(channelsContainer, VaadinApp.this);
    private FeedsTable feeds_table = new FeedsTable(feedsContainer, VaadinApp.this);
	
    //Storage
    private StorageService storageService;
	public VaadinApp(StorageService storageService) {
		this.storageService = storageService;
	}
	public StorageService getStorageService() {
		return storageService;
	}
		
	@Override
	public void init() {
		initDataSource();
		updateChannelsTable();
		buildMainLayout();
		horizontalSplit.setFirstComponent(channels_table);
		horizontalSplit.setSecondComponent(feeds_table);
		new_channel.addListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;
			@Override
			public void buttonClick(ClickEvent event) {		
				if (channel_form_window != null) {
					getMainWindow().removeWindow(channel_form_window);
				}
				
				BeanItem<Channel> beanChannelItem = new BeanItem<Channel>(new Channel());
				ChannelWindowFormFactory cff  = new ChannelWindowFormFactory(beanChannelItem, VaadinApp.this);
				
				channel_form_window = cff.createWindow();
				getMainWindow().addWindow(channel_form_window);
			}
		});
	}

	public void updateChannelsTable(){
		channelsContainer.removeAllItems();
		channelsContainer.addAll(storageService.getAllchannels());
	}
	
	public void updateFeedsTable(){
		feedsContainer.removeAllItems();
		feedsContainer.addAll(storageService.getAllfeeds());
	}
	
	private void buildMainLayout() {
		setMainWindow(new Window("Rss Reader application"));
	    VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();        
        layout.addComponent(createToolbar());
        layout.addComponent(horizontalSplit);
        layout.setExpandRatio(horizontalSplit, 1);
        horizontalSplit.setSplitPosition(400, SplitPanel.UNITS_PIXELS);
        getMainWindow().setContent(layout);
	}
	
	public HorizontalLayout createToolbar() {
        HorizontalLayout lo = new HorizontalLayout();
        lo.addComponent(new_channel);
        return lo;
    }
	
	private void initDataSource(){
		Channel c1 = new Channel();
		c1.setName("Wirtualna Polska Sport");
		c1.setLink("http://www.wp.pl/rss.xml?id=2");
		storageService.addChannel(c1);

		Channel c2 = new Channel();
		c2.setName("Wirtualna Polska Technologie");
		c2.setLink("http://www.wp.pl/rss.xml?id=4");
		storageService.addChannel(c2);
	}
}
