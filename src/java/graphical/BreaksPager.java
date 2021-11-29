package graphical;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Michail Sitmalidis
 */
public class BreaksPager {

    private int currentPage;
    private String display;

    private int lastPageNumber;
    private ArrayList<Route> routeBreakVersions;
    private int displayedVersionsLimit;

    public BreaksPager(ArrayList<Route> routeBreakVersions, int displayedVersionsLimit) {
        this.routeBreakVersions = routeBreakVersions;
        this.displayedVersionsLimit = displayedVersionsLimit;
        if (routeBreakVersions.size() % displayedVersionsLimit == 0) {
            lastPageNumber = routeBreakVersions.size() / displayedVersionsLimit;
        } else {
            lastPageNumber = (routeBreakVersions.size() / displayedVersionsLimit) + 1;
        }

    }

    public List getCurrentVersions() {
        if (routeBreakVersions.size() > displayedVersionsLimit) {
            if ((this.currentPage - 1) * displayedVersionsLimit + displayedVersionsLimit > routeBreakVersions.size()) {
                int startPoint = (this.currentPage - 1) * displayedVersionsLimit;
                int endPoint = startPoint + (routeBreakVersions.size() - (this.currentPage - 1) * displayedVersionsLimit);
                List subList = routeBreakVersions.subList(startPoint, endPoint);
                return subList;
            } else {
                List subList = routeBreakVersions.subList((this.currentPage - 1) * displayedVersionsLimit, (this.currentPage - 1) * displayedVersionsLimit + displayedVersionsLimit);
                return subList;
            }
        } else {
            return routeBreakVersions;
        }
    }

    public int getVersionsCount() {
        return routeBreakVersions.size();
    }

    public void setCurrentPage(int currentPageNumber) {
        this.currentPage = currentPageNumber;
        int prePreviosPageNumber = currentPageNumber - 5;
        int previosPageNumber = currentPageNumber - 1;
        int nextPageNumber = currentPageNumber + 1;
        int nextNextPageNumber = currentPageNumber + 5;
        StringBuilder prePreviousPageHTML = new StringBuilder();
        StringBuilder previousPageHTML = new StringBuilder();
        StringBuilder nextPageHTML = new StringBuilder();
        StringBuilder nextNextPageHTML = new StringBuilder();
        if (prePreviosPageNumber > 0) {
            prePreviousPageHTML = prePreviousPageHTML.append("<a href=\"breaksPageRequest.htm?requestedPage=").append(prePreviosPageNumber).append("\">").append(prePreviosPageNumber).append("</a>");
        }
        if (previosPageNumber > 0) {
            previousPageHTML = previousPageHTML.append("<a href=\"breaksPageRequest.htm?requestedPage=").append(previosPageNumber).append("\">").append(previosPageNumber).append("</a>");
        }

        StringBuilder currentPageHTML = new StringBuilder("<a class=\"active\">").append(currentPageNumber).append("</a>");
        if (nextPageNumber <= lastPageNumber) {
            nextPageHTML = nextPageHTML.append("<a href=\"breaksPageRequest.htm?requestedPage=").append(nextPageNumber).append("\">").append(nextPageNumber).append("</a>");
        }
        if (nextNextPageNumber <= lastPageNumber) {
            nextNextPageHTML.append("<a href=\"breaksPageRequest.htm?requestedPage=").append(nextNextPageNumber).append("\">").append(nextNextPageNumber).append("</a>");
        }

        StringBuilder stringBuilder = new StringBuilder("<div class=\"pagination\">");
        stringBuilder = stringBuilder.append(prePreviousPageHTML);
        stringBuilder = stringBuilder.append(previousPageHTML);
        stringBuilder = stringBuilder.append(currentPageHTML);
        stringBuilder = stringBuilder.append(nextPageHTML);
        stringBuilder = stringBuilder.append(nextNextPageHTML);
        stringBuilder = stringBuilder.append("</div>");

        display = stringBuilder.toString();

    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public int getBlockStartingVersionNumber() {
        return (currentPage-1) * displayedVersionsLimit;
    }

}
