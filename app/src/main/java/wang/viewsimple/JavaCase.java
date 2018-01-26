package wang.viewsimple;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * JavaCase
 * Created by Wang on 2017/10/20.
 */
public class JavaCase {

    public static void main(String args[]) {
        System.out.println("hello world!");
        File path = new File(".");
        String[] list;
        if (args.length == 0) {
            list = path.list();
        } else {
            System.out.println("args[0]=" + args[0]);
            list = path.list(new DirFilter(args[0]));
        }
        Arrays.sort(list, String.CASE_INSENSITIVE_ORDER);
        for (String dirItem : list) {
            System.out.println(dirItem);
        }
    }

    static class DirFilter implements FilenameFilter {

        private Pattern pattern;

        public DirFilter(String regex) {
            pattern = Pattern.compile(regex);
        }

        @Override
        public boolean accept(File dir, String name) {
            return pattern.matcher(name).matches();
        }
    }

}
