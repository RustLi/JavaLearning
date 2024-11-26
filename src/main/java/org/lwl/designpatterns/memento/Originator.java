package org.lwl.designpatterns.memento;

public class Originator {
    private String state;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    /**
     * des: 保存状态到备忘录，并返回该状态到备忘录，用于Caretaker保存
     * @param
     */
    public Memento saveStateToMemento(String state){
        return new Memento(state);
    }

    /**
     * des: 从memento中获取状态，保存到全局的state
     * @param
     */
    public void restoreStateFromMemento(Memento memento){
        this.state =  memento.getState();
    }
}
