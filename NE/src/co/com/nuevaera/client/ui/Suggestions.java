package co.com.nuevaera.client.ui;




import com.google.gwt.user.client.rpc.IsSerializable;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;


public class Suggestions implements IsSerializable, Suggestion, Comparable<Suggestions> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	private String suggestions;
	private long id;
	
	
	public Suggestions(String suggestion, long id) {
		this.suggestions = suggestion;
		this.id = id;
	}
	
	public String getSuggestions() {
		return suggestions;
	}

	public void setSuggestions(String suggestions) {
		this.suggestions = suggestions;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDisplayString() {
		return suggestions;
	}

	public String getReplacementString() {
		return suggestions;
	}
	
	public long getId(){
		return id;
	}

	@Override
	public int compareTo(Suggestions o) {
		return this.suggestions.compareTo(o.getSuggestions());
		/*char value[] = this.suggestions.toCharArray();
		char anotherString[] = o.suggestions.toCharArray();
        int len1 = value.length;
        int len2 = anotherString.length;
        int lim = Math.min(len1, len2);
        char v1[] = value;
        char v2[] = anotherString;

        int k = 0;
        while (k < lim) {
            char c1 = v1[k];
            char c2 = v2[k];
            if (c1 != c2) {
                return c1 - c2;
            }
            k++;
        }
        return len1 - len2;*/
	}



}