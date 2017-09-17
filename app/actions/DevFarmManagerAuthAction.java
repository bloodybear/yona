package actions;

import controllers.DevFarm;
import controllers.UserApp;
import play.libs.F;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;
import utils.ErrorViews;

public class DevFarmManagerAuthAction extends Action.Simple {
    @Override
    public F.Promise<Result> call(Http.Context ctx) throws Throwable {
        if ( !(UserApp.currentUser().isSiteManager() || DevFarm.isAdmin(UserApp.currentUser())) ) {
            return F.Promise.pure((Result) forbidden(ErrorViews.Forbidden.render("error.auth.unauthorized.waringMessage")));
        }
        return delegate.call(ctx);
    }
}
