package Model;

import java.util.LinkedHashMap;

public class TripPeriodsPager {

    private String display;
    private String initialDispaly;
    private int lastPageNumber;
    private LinkedHashMap<String, Integer> routeNumbers;

    public TripPeriodsPager(LinkedHashMap<String, Integer> routeNumbers, int rowCount, int rowLimit) {
        this.routeNumbers = routeNumbers;
        lastPageNumber = rowCount / rowLimit;
        initialDispaly = "<div class=\"pagination\">\n"
                + "  <a href=\"#\" class=\"active\">1</a>\n"
                + "  <a href=\"tripPeriodsRequest.htm?requestedPage=2\" >2</a>\n"
                + "  <a href=\"tripPeriodsRequest.htm?requestedPage=3\">3</a>\n"
                + "  <a href=\"tripPeriodsRequest.htm?requestedPage=4\">4</a>\n"
                + "  <a href=\"tripPeriodsRequest.htm?requestedPage=5\">5</a>\n"
                + "  <a href=\"tripPeriodsRequest.htm?requestedPage=2\">&raquo;</a>\n"
                + "  <a href=\"tripPeriodsRequest.htm?requestedPage=" + lastPageNumber + "\">" + lastPageNumber + "</a>\n"
                + "</div>";
        display = initialDispaly;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public void setCurrentPage(int currentPageNumber) {
        if (currentPageNumber == 1) {
            display = initialDispaly;
            return;
        }
        if (currentPageNumber == 2) {
            display = "<div class=\"pagination\">\n"
                    + "  <a href=\"tripPeriodsRequest.htm?requestedPage=1\">&laquo;</a>\n"
                    + "  <a href=\"tripPeriodsRequest.htm?requestedPage=1\" >1</a>\n"
                    + "  <a href=\"#\" class=\"active\">2</a>\n"
                    + "  <a href=\"tripPeriodsRequest.htm?requestedPage=3\">3</a>\n"
                    + "  <a href=\"tripPeriodsRequest.htm?requestedPage=4\">4</a>\n"
                    + "  <a href=\"tripPeriodsRequest.htm?requestedPage=5\">5</a>\n"
                    + "  <a href=\"tripPeriodsRequest.htm?requestedPage=2\">&raquo;</a>\n"
                    + "  <a href=\"tripPeriodsRequest.htm?requestedPage=" + lastPageNumber + "\">" + lastPageNumber + "</a>\n"
                    + "  <a href=\"#\">&#10704;</a>\n"
                    + "</div>";
            return;
        }
        if (currentPageNumber < lastPageNumber - 2) {
            int previosPageNumber = currentPageNumber - 1;
            int prePreviosPageNumber = currentPageNumber - 2;
            int nextPageNumber = currentPageNumber + 1;
            int nextNextPageNumber = currentPageNumber + 2;

            StringBuilder stringBuilder = new StringBuilder("<div class=\"pagination\">");
            stringBuilder = stringBuilder.append("<a href=\"tripPeriodsRequest.htm?requestedPage=1\">1</a>");
            stringBuilder = stringBuilder.append("<a href=\"tripPeriodsRequest.htm?requestedPage=");
            stringBuilder = stringBuilder.append(String.valueOf(previosPageNumber));
            stringBuilder = stringBuilder.append("\">&laquo;</a>");

            stringBuilder = stringBuilder.append("<a href=\"tripPeriodsRequest.htm?requestedPage=");
            stringBuilder = stringBuilder.append(String.valueOf(prePreviosPageNumber));
            stringBuilder = stringBuilder.append("\">");
            stringBuilder = stringBuilder.append(String.valueOf(prePreviosPageNumber));
            stringBuilder = stringBuilder.append("</a>");

            stringBuilder = stringBuilder.append("<a href=\"tripPeriodsRequest.htm?requestedPage=");
            stringBuilder = stringBuilder.append(String.valueOf(previosPageNumber));
            stringBuilder = stringBuilder.append("\">");
            stringBuilder = stringBuilder.append(String.valueOf(previosPageNumber));
            stringBuilder = stringBuilder.append("</a>");

            stringBuilder = stringBuilder.append("<a href=\"tripPeriodsRequest.htm?requestedPage=");
            stringBuilder = stringBuilder.append(String.valueOf(currentPageNumber));
            stringBuilder = stringBuilder.append("\" class=\"active\">");
            stringBuilder = stringBuilder.append(String.valueOf(currentPageNumber));
            stringBuilder = stringBuilder.append("</a>");

            stringBuilder = stringBuilder.append("<a href=\"tripPeriodsRequest.htm?requestedPage=");
            stringBuilder = stringBuilder.append(String.valueOf(nextPageNumber));
            stringBuilder = stringBuilder.append("\">");
            stringBuilder = stringBuilder.append(String.valueOf(nextPageNumber));
            stringBuilder = stringBuilder.append("</a>");

            stringBuilder = stringBuilder.append("<a href=\"tripPeriodsRequest.htm?requestedPage=");
            stringBuilder = stringBuilder.append(String.valueOf(nextNextPageNumber));
            stringBuilder = stringBuilder.append("\">");
            stringBuilder = stringBuilder.append(String.valueOf(nextNextPageNumber));
            stringBuilder = stringBuilder.append("</a>");

            stringBuilder = stringBuilder.append("<a href=\"tripPeriodsRequest.htm?requestedPage=");
            stringBuilder = stringBuilder.append(String.valueOf(nextPageNumber));
            stringBuilder = stringBuilder.append("\">&raquo;</a>\n");

            stringBuilder = stringBuilder.append("<a href=\"tripPeriodsRequest.htm?requestedPage=").append(lastPageNumber).append("\">").append(lastPageNumber).append("</a>\n");

            display = stringBuilder.toString();

        } else {
            if (currentPageNumber == lastPageNumber - 2) {
            }
        }
    }

    public LinkedHashMap<String, Integer> getRouteNumbers() {
        return routeNumbers;
    }

    public void setRouteNumbers(LinkedHashMap<String, Integer> routeNumbers) {
        this.routeNumbers = routeNumbers;
    }

}
