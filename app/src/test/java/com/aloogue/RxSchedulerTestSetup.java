package com.aloogue;

import java.util.concurrent.TimeUnit;

import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.schedulers.ExecutorScheduler;
import io.reactivex.plugins.RxJavaPlugins;

public class RxSchedulerTestSetup {

    public static void setupRxScheduler() {
        Scheduler imediate = new Scheduler() {
            @Override
            public Disposable scheduleDirect(@NonNull Runnable run, long delay, @NonNull TimeUnit unit) {
                return super.scheduleDirect(run, 0, unit);
            }

            @Override
            public Worker createWorker() {
                return new ExecutorScheduler.ExecutorWorker(Runnable::run);
            }
        };

        RxJavaPlugins.setInitIoSchedulerHandler(scheduler -> imediate);
        RxJavaPlugins.setInitComputationSchedulerHandler(scheduler -> imediate);
        RxJavaPlugins.setInitNewThreadSchedulerHandler(scheduler -> imediate);
        RxJavaPlugins.setInitSingleSchedulerHandler(scheduler -> imediate);
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(scheduler -> imediate);
    }
}
