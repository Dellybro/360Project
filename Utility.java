public class Utility {

  /* Strings */
  public final static String lines = "Lines";
  public final static String blankLines = "Blank Lines";
  public final static String spaces = "Spaces";
  public final static String words = "Words";
  public final static String averageCharPerLine = "Average Chars Per Line";
  public final static String averageWordLength = "Average Word Length";
  public final static String mostCommonWord = "Most Common Word";

  public static String[] mergeStringArrays(final String[] ...arrays ) {
    int size = 0;
    for ( String[] a: arrays )
        size += a.length;

        String[] res = new String[size];

        int destPos = 0;
        for ( int i = 0; i < arrays.length; i++ ) {
            if ( i > 0 ) destPos += arrays[i-1].length;
            int length = arrays[i].length;
            System.arraycopy(arrays[i], 0, res, destPos, length);
        }

        return res;
  }

  public static Object[] mergeObjectArrays(final Object[] ...arrays ) {
    int size = 0;
    for ( Object[] a: arrays )
        size += a.length;

        Object[] res = new Object[size];

        int destPos = 0;
        for ( int i = 0; i < arrays.length; i++ ) {
            if ( i > 0 ) destPos += arrays[i-1].length;
            int length = arrays[i].length;
            System.arraycopy(arrays[i], 0, res, destPos, length);
        }

        return res;
  }
}
