
/**
 * A Tagged Token. eg. "Token/NN"
 * @author michael
 */
public class TaggedToken {
	private static final String POS_SEP = "/";
	private static final int TOKEN_INDEX = 0;
	private static final int POS_TAG_INDEX = 1;

	private String[] parsedValue;

	public TaggedToken(String taggedToken) {
		this.parsedValue = new String[2];
		this.parsedValue[TOKEN_INDEX] = taggedToken.substring(0, taggedToken.lastIndexOf(POS_SEP));
		this.parsedValue[POS_TAG_INDEX] = taggedToken.substring(taggedToken.lastIndexOf(POS_SEP) + 1);
	}

	public TaggedToken(String token, String posTag) {
		this.parsedValue = new String[]{token, posTag};
	}

	public String getToken() {
		return this.parsedValue[TOKEN_INDEX];
	}

	public String getPosTag() {
        return this.parsedValue[POS_TAG_INDEX];
    }

    @Override
    public String toString() {
        return this.getToken() + POS_SEP + this.getPosTag();
    }

    @Override
    public boolean equals(Object o) {
    	if (o instanceof TaggedToken) {
    		return ((TaggedToken) o).getToken().equals(this.getToken()) &&
    			((TaggedToken) o).getPosTag().equals(this.getPosTag());
    	}
    	return false;
    }
}
