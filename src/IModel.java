public interface IModel {
	/**
	 * P(t|<s>)
	 * @param t
	 * @return
	 */
	public double getProbablityOfTagGivenStart(String t);

	/**
	 * P(</s>|t)
	 * @param t
	 * @return
	 */
	public double getProbabilityOfEndGivenTag(String t);

	/**
	 * P(w|t)
	 * @param w
	 * @param t
	 * @return
	 */
	public double getProbabilityOfWordGivenTag(String w, String t);

	/**
	 * P(t2|t1)
	 * @param t2
	 * @param t1
	 * @return
	 */
	public double getProbabilityOfNextTagGivenTag(String t2, String t1);
}
