package com.cleanarch.base.usecases;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subscribers.DisposableSubscriber;

public abstract class UseCase<InputType, OutputType> {

    protected CompositeDisposable disposables = new CompositeDisposable();
    public abstract void execute(InputType input, DisposableSubscriber<OutputType> subscriber);

    public void cancel() {
        disposables.clear();
    }
}
