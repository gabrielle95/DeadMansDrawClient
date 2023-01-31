package herebcs.spcjavaclient.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import herebcs.spcjavaclient.types.Card;

/**
 *
 * @author I816768
 */

@JsonIgnoreProperties(ignoreUnknown=true)
public class EffectResponse {
    
    public class Effect {
        public String effectType;
        public Card card;
    }
    
    public final String etype = "ResponseToEffect";
    public Effect effect = new Effect();
    
}
