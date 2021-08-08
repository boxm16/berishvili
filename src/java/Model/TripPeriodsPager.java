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
        if (lastPageNumber < 5) {
            initialDispaly = "<div class=\"pagination\">\n"
                    + "  <a href=\"#\" class=\"active\">1</a>\n"
                    + "  <a href=\"tripPeriodsRequest.htm?requestedPage=2\" >+1</a>\n"
                    + "</div>";
        }
        if (lastPageNumber > 5) {
            initialDispaly = "<div class=\"pagination\">\n"
                    + "  <a href=\"#\" class=\"active\">1</a>\n"
                    + "  <a href=\"tripPeriodsRequest.htm?requestedPage=2\" >+1</a>\n"
                    + "  <a href=\"tripPeriodsRequest.htm?requestedPage=6\">+5</a>\n"
                    + "</div>";
        }

        display = initialDispaly;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public void setCurrentPage(int currentPageNumber) {
        int prePreviosPageNumber = currentPageNumber - 5;
        int previosPageNumber = currentPageNumber - 1;
        int nextPageNumber = currentPageNumber + 1;
        int nextNextPageNumber = currentPageNumber + 5;
        StringBuilder prePreviousPageHTML = new StringBuilder();
        StringBuilder previousPageHTML = new StringBuilder();
        if (prePreviosPageNumber > 0) {
            prePreviousPageHTML = prePreviousPageHTML.append("<a href=\"tripPeriodsRequest.htm?requestedPage=").append(prePreviosPageNumber).append("\">").append(prePreviosPageNumber).append("</a>");
        }
        if (previosPageNumber > 0) {
            previousPageHTML = previousPageHTML.append("<a href=\"tripPeriodsRequest.htm?requestedPage=").append(previosPageNumber).append("\">").append(previosPageNumber).append("</a>");
        }
        StringBuilder currentPageHTML = new StringBuilder("<a class=\"active\">").append(currentPageNumber).append("</a>");
        StringBuilder nextPageHTML = new StringBuilder("<a href=\"tripPeriodsRequest.htm?requestedPage=").append(nextPageNumber).append("\">").append(nextPageNumber).append("</a>");
        StringBuilder nextNextPageHTML = new StringBuilder("<a href=\"tripPeriodsRequest.htm?requestedPage=").append(nextNextPageNumber).append("\">").append(nextNextPageNumber).append("</a>");

        StringBuilder stringBuilder = new StringBuilder("<div class=\"pagination\">");
        stringBuilder = stringBuilder.append(prePreviousPageHTML);
        stringBuilder = stringBuilder.append(previousPageHTML);
        stringBuilder = stringBuilder.append(currentPageHTML);
        stringBuilder = stringBuilder.append(nextPageHTML);
        stringBuilder = stringBuilder.append(nextNextPageHTML);
        stringBuilder = stringBuilder.append("</div>");

        display = stringBuilder.toString();

    }

    public LinkedHashMap<String, Integer> getRouteNumbers() {
        return routeNumbers;
    }

    public void setRouteNumbers(LinkedHashMap<String, Integer> routeNumbers) {
        this.routeNumbers = routeNumbers;
    }

}
