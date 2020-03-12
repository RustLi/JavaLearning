package design_patterns.memento;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CareTaker {
    //也可采用map
    private List<Memento> mementoList = new ArrayList<>();

    public void addMemento(Memento state){
        mementoList.add(state);
    }

    public Memento getMemento(int index){
        return mementoList.get(index);
    }
}
