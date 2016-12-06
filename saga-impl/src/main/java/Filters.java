import play.http.HttpFilters;
import play.mvc.EssentialFilter;
import play.filters.cors.CORSFilter;
import play.http.DefaultHttpFilters;

import javax.inject.Inject;

public class Filters implements HttpFilters {

    private final EssentialFilter[] filters;

    @Inject
    public Filters(final CORSFilter corsFilter) {
        filters = new EssentialFilter[]{corsFilter.asJava()};
    }

    @Override
    public EssentialFilter[] filters() {
        return filters;
    }
}
