package bg.uni_sofia.fmi.explore.filters;

import java.util.List;

import bg.uni_sofia.fmi.explore.models.PlaceModel;
import rx.Observable;
import rx.functions.Func2;

/**
 * Created by eyanev on 5/25/15.
 */
public class PlaceAlphaCriteria implements PlaceCriteria {
    @Override
    public Observable<List<PlaceModel>> meetCriteria(List<PlaceModel> places) {
        return Observable.from(places)
                .toSortedList(new Func2<PlaceModel, PlaceModel, Integer>() {
                    @Override
                    public Integer call(PlaceModel placeModel, PlaceModel placeModel2) {
                        return placeModel.getPlaceName().compareTo(placeModel2.getPlaceName());
                    }
                });
    }
}
