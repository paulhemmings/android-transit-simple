package com.razor.transit.handlers;


import android.content.Context;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.gson.annotations.SerializedName;
import com.razor.transit.comms.IReply;
import com.razor.transit.comms.IReplyHandler;
import com.razor.transit.comms.IRequest;
import com.razor.transit.comms.IRequestContent;
import com.razor.transit.comms.replies.ModelReply;
import com.razor.transit.models.RouteCollectionDTO;
import com.razor.transit.models.RouteDTO;
import com.razor.transit.models.StopCollectionDTO;
import com.razor.transit.models.StopDTO;
import com.razor.transit.viewmodels.IViewModel;
import com.razor.transit.viewmodels.RouteViewModel;
import com.razor.transit.viewmodels.StopViewModel;
import razor.android.transit.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class StopsHandler extends BaseHandler implements IReplyHandler
{

    public static class StopRequestContent
            extends IRequestContent
            implements Serializable {
        @SerializedName("agencyName")
        public String agencyName;

        @SerializedName("routeCode")
        public String routeCode;
    }

    public static class StopsInBoundsRequestContent
            extends IRequestContent
            implements Serializable {

        StopsInBoundsRequestContent(final String filterAgencyName,
                                    final LatLngBounds bounds) {
            this.agencyName = filterAgencyName;
            this.southwestLatitude = bounds.southwest.latitude;
            this.southwestLongitude = bounds.southwest.longitude;
            this.northEastLatitude = bounds.northeast.latitude;
            this.northEastLongitude = bounds.northeast.longitude;
        }

        @SerializedName("agencyName")
        public String agencyName;

        @SerializedName("southwestLatitude")
        public double southwestLatitude;

        @SerializedName("southwestLongitude")
        public double southwestLongitude;

        @SerializedName("northEastLatitude")
        public double northEastLatitude;

        @SerializedName("northEastLongitude")
        public double northEastLongitude;
    }

    /**
     * notification of routes retrieval
     * @author phemmings
     */

    public interface OnStopsLoadListener
    {
        public void onStopsLoaded(List<IViewModel> routes);
        public void onStopsLoadFailed(String errorMessage);
    }

	/*
	 * Properties
	 */

    protected OnStopsLoadListener stopsLoadListener;

    /**
     * List Events
     * @param listener : OnStopsLoadListener
     * @param agencyName : String
     * @param routeCode : String
     */

    public void requestStops(final OnStopsLoadListener listener,
                             final String agencyName,
                             final String routeCode) {

        this.stopsLoadListener = listener;

        StopRequestContent requestContent = new StopRequestContent();
        requestContent.agencyName = agencyName;
        requestContent.routeCode = routeCode;

        super.getRequestHandler().handleRequest(
                R.string.request_stop_list,
                requestContent,
                this);
    }

    public void requestStops(final OnStopsLoadListener listener,
                             final String agencyName,
                             final LatLngBounds bounds) {

        this.stopsLoadListener = listener;

        StopsInBoundsRequestContent requestContent
                = new StopsInBoundsRequestContent(agencyName, bounds);

        super.getRequestHandler().handleRequest(
                R.string.request_stops_in_bounds,
                requestContent,
                this);
    }

    /**
     * Handle reply
     * @param reply : IReply
     * @param request : IRequest
     */

    public void onReply(final IRequest request,
                        final IReply reply) {

        if (reply.getResponseCode() == 0) {
            ModelReply<StopCollectionDTO> modelReply = (ModelReply<StopCollectionDTO>) reply;
            StopCollectionDTO collectionDTO = modelReply.getModel();

            List<IViewModel> list = new ArrayList<IViewModel>();
            if (collectionDTO != null && collectionDTO.getStops().size() >0) {
                for (StopDTO item: collectionDTO.getStops()){
                    StopViewModel model = new StopViewModel();
                    model.setModel(item);
                    list.add(model);
                }
            }
            this.stopsLoadListener.onStopsLoaded(list);
        } else {
            this.stopsLoadListener.onStopsLoadFailed(reply.getErrorMessage());
        }
    }

}

