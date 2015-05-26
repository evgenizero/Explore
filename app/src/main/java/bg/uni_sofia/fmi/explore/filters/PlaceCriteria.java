package bg.uni_sofia.fmi.explore.filters;

import java.util.List;

import bg.uni_sofia.fmi.explore.models.PlaceModel;
import rx.Observable;

/**
 * Created by eyanev on 5/25/15.
 */
public interface PlaceCriteria {
    public Observable<List<PlaceModel>> meetCriteria(List<PlaceModel> places);
}
