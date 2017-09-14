package alugueis.alugueis.viper.arquitecture;


import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public abstract class Interactor<T> implements Disposable {
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();


    protected void execute(@NonNull Observable<T> observable) {
        this.executeUniqueResult(observable, null);
    }

    protected void executeUniqueResult(@NonNull Observable<T> observable, @NonNull DisposableObserver<T> disposableObserver) {
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(disposableObserver);

        addDisposableToComposite(disposableObserver);
    }

    protected void executeListResult(@NonNull Observable<List<T>> observable, @NonNull DisposableObserver<List<T>> disponsableObserver) {
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(disponsableObserver);
        addListTypeDisposableToComposite(disponsableObserver);
    }


    private void addDisposableToComposite(DisposableObserver<T> disposableObserver) {
        compositeDisposable.add(disposableObserver);
    }

    private void addListTypeDisposableToComposite(DisposableObserver<List<T>> disposableObserver) {
        compositeDisposable.add(disposableObserver);
    }

    @Override
    public void dispose() {
        compositeDisposable.dispose();
    }

    @Override
    public boolean isDisposed() {
        return compositeDisposable.isDisposed();
    }
}
