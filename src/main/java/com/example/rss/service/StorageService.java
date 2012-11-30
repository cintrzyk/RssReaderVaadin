package com.example.rss.service;

import java.util.ArrayList;
import java.util.List;

import org.horrabin.horrorss.RssFeed;
import org.horrabin.horrorss.RssItemBean;
import org.horrabin.horrorss.RssParser;

import com.example.rss.domain.Channel;
import com.example.rss.domain.Feed;

public class StorageService {

	private List<Channel> channels = new ArrayList<Channel>();
	private List<Feed> feeds = new ArrayList<Feed>();

///CHANNELS
	public void addChannel(Channel channel) {
		Channel ch = new Channel();
		ch.setName(channel.getName());
		ch.setLink(channel.getLink());
		channels.add(ch);
	}

	public List<Channel> getAllchannels() {
		return channels;
	}

	public void deleteChannel(Channel ch) {
		Channel toRemove = null;
		for (Channel channel : channels){
			if (channel.getLink() == ch.getLink()) {
				toRemove = channel;
				break;
			}
		}
		channels.remove(toRemove);
	}

/// FEEDS
	public void addFeeds(String url) {
		RssFeed feed = getFeed(url);
		List<RssItemBean> items = feed.getItems();
        
		for (int i=0; i<items.size(); i++){
             RssItemBean item = items.get(i);
             feeds.add(new Feed(item.getLink(), item.getDescription(), item.getPubDate(), item.getTitle()));
        }
	}

	public List<Feed> getAllfeeds() {
		return feeds;
	}
	
	public void removeAllFeeds(){
		feeds.removeAll(feeds);
	}

	public void deleteFeed(Feed fd) {
		Feed toRemove = null;
		for (Feed feed : feeds){
			if (feed.getUrl() == fd.getUrl()) {
				toRemove = feed;
				break;
			}
		}
		feeds.remove(toRemove);
	}
	
	private RssFeed getFeed(String url) {
		RssParser rss = new RssParser();
		try{
			RssFeed feed = rss.load(url); 
			return feed;
		}catch(Exception e){	}
		return null;
	}
}
