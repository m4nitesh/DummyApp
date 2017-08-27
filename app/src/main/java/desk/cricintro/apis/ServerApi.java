package desk.cricintro.apis;



import desk.cricintro.models.ZomatoSearchModel;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;
import rx.Observable;

public class ServerApi {

    public interface ZomatoSearchService{
        @GET("search")
        Observable<ZomatoSearchModel> getSearchResults(@Header("user-key") String key, @Query("q") String query);
    }

}
