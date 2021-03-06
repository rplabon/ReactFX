package org.reactfx.inhibeans.property;

import org.reactfx.Hold;

/**
 * Inhibitory version of {@link javafx.beans.property.LongPropertyBase}.
 */
public abstract class LongPropertyBase
extends javafx.beans.property.LongPropertyBase
implements Property<Number> {

    private boolean blocked = false;
    private boolean fireOnRelease = false;

    @Override
    public Hold block() {
        if(blocked) {
            return Hold.EMPTY_HOLD;
        } else {
            blocked = true;
            return this::release;
        }
    }

    private void release() {
        blocked = false;
        if(fireOnRelease) {
            fireOnRelease = false;
            super.fireValueChangedEvent();
        }
    }

    @Override
    protected void fireValueChangedEvent() {
        if(blocked)
            fireOnRelease = true;
        else
            super.fireValueChangedEvent();
    }


    /********************************
     *** Superclass constructors. ***
     ********************************/

    public LongPropertyBase() {
        super();
    }

    public LongPropertyBase(long initialValue) {
        super(initialValue);
    }
}
