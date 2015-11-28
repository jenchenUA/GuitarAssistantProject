package jenchenua.guitarassistantapp.draw.interfaces;

import jenchenua.guitarassistantapp.draw.itemsfordrawing.Cross;
import jenchenua.guitarassistantapp.draw.itemsfordrawing.Dot;
import jenchenua.guitarassistantapp.draw.itemsfordrawing.Fret;
import jenchenua.guitarassistantapp.draw.itemsfordrawing.GuitarString;

public interface Fingering {
    void drawDot(Dot dot);
    void drawFret(Fret fret);
    void drawGuitarString(GuitarString string);
    void drawCross(Cross cross);
}
