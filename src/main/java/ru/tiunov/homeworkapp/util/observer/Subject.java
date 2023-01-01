package ru.tiunov.homeworkapp.util.observer;

public interface Subject {
    void register(Observer observer);
    void notifyObservers();
}
