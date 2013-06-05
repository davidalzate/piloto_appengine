package co.com.nuevaera.client;


import co.com.nuevaera.client.view.RestauranteViewImpl;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class NE implements EntryPoint {


	private final NEServiceAsync neService = GWT
			.create(NEService.class);

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		Window.enableScrolling(true);
		Window.setMargin("0px");
		HandlerManager eventBus = new HandlerManager(null);
		AppController appController = new AppController(neService, eventBus);
		RootLayoutPanel root = RootLayoutPanel.get();
		appController.go(root);		
	}
}
