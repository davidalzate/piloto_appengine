package co.com.nuevaera.client.ui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import co.com.nuevaera.client.NEService;
import co.com.nuevaera.client.NEServiceAsync;
import co.com.nuevaera.client.dto.RestauranteDto;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.SuggestOracle;

public class SuggestionOracle extends MultiWordSuggestOracle {

	private final NEServiceAsync neService = GWT
			.create(NEService.class);

	public void requestSuggestions(SuggestOracle.Request req,
			SuggestOracle.Callback callback) {
		neService.getRestaurantes(new ItemRequestCallback(req, callback));
	}

	public boolean isDisplayStringHTML() {
		return true;
	}

	public class ItemRequestCallback implements
			AsyncCallback<List<RestauranteDto>> {
		private SuggestOracle.Request req;
		private SuggestOracle.Callback callback;

		public void onFailure(Throwable caught) {
			callback.onSuggestionsReady(req, new SuggestOracle.Response());
			caught.printStackTrace();
		}

		public ItemRequestCallback(SuggestOracle.Request _req,
				SuggestOracle.Callback _callback) {
			this.req = _req;
			this.callback = _callback;
		}

		@Override
		public void onSuccess(List<RestauranteDto> result) {
			
			List<Suggestions> suggestionsList = new ArrayList<Suggestions>();
			for (Iterator iterator = result.iterator(); iterator.hasNext();) {
				RestauranteDto restauranteDto = (RestauranteDto) iterator
						.next();
				suggestionsList.add(new Suggestions(restauranteDto.getNombre(), restauranteDto.getIdRestaurante()));
			}
			SuggestOracle.Response response = new SuggestOracle.Response();
			response.setSuggestions(suggestionsList);
			callback.onSuggestionsReady(req, (SuggestOracle.Response) response);

		}
	}
}