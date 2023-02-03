package herebcs.spcjavaclient.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import herebcs.spcjavaclient.types.Card;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Event {
    public String eventType;

    public Card drawCard;

    public Card cardPlacedToPlayAreaCard;
}
