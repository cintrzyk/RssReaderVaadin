package com.example.rss.ui;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.terminal.ExternalResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.Window;
import com.vaadin.ui.VerticalLayout;

public class DescriptionPopUp extends VerticalLayout {
	private static final long serialVersionUID = 1L;
	
    Window subwindow;
    
	public DescriptionPopUp(String title, String description, String url) {
	    subwindow = new Window(title);
        subwindow.setCloseShortcut(KeyCode.ESCAPE, null);

        VerticalLayout layout = (VerticalLayout) subwindow.getContent();
        layout.setMargin(true);
        layout.setSpacing(true);

        Label message = new Label(description);
        Link link = new Link("Źródło", new ExternalResource(url));
       
        subwindow.addComponent(message);
        subwindow.addComponent(link);
        
        subwindow.center();
        subwindow.setWidth("25%");
        
        setSpacing(true);
        
        Button close = new Button("Close", new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
        	public void buttonClick(ClickEvent event) {
                subwindow.getParent().removeWindow(subwindow);
            }
        });

        layout.addComponent(close);
        layout.setComponentAlignment(close, Alignment.TOP_RIGHT);
    }
	
	public Window createWindow(){
        if (subwindow.getParent() != null) {
            subwindow.getParent().removeWindow(subwindow);
        } 
        return subwindow;
	}
	
}