package com.razor.transit.viewmodels;

import com.razor.transit.models.StopDTO;

public class StopViewModel extends ModelContainer<StopDTO> implements IViewModel
{
    private String mapMarkerId;

    @Override
    public Long getID()
    {
        return null;
    }

    @Override
    public String getCode()
    {
        return this.getModel().getStopCode();
    }

    @Override
    public String getTitle()
    {
        return this.getModel().getName();
    }

    public String getDetails()
    {
        return String.format("%d %d",
                this.getModel().getLocation().getLatitude(),
                this.getModel().getLocation().getLongitude());
    }

    public String getMapMarkerId()
    {
        return mapMarkerId;
    }

    public void setMapMarkerId(final String mapMarkerId)
    {
        this.mapMarkerId = mapMarkerId;
    }
}
