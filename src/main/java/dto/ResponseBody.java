package dto;

import lombok.Data;
import java.util.List;

@Data
public class ResponseBody {

    private List<Item> items;
    private int found;

    @Data
    public static class Item {
        private int id;
        private String name;
        private Area area;
        private Salary salary;
        private Employer employer;
        private Snippet snippet;

        @Data
        public static class Area {
            private int id;
            private String name;
        }

        @Data
        public static class Salary {
            private int from;
            private int to;
            private String currency;
            private boolean gross;
        }

        @Data
        public static class Employer {
            private int id;
            private String name;
        }

        @Data
        public static class Snippet {
            private String requirement;
            private String responsibility;
        }
    }
}
