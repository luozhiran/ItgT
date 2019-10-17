package com.sup.itg.itgt.java.animation.behavior.appbarBehaviorlearn.util;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CustomPools {


    public CustomPools() {
    }

    public interface Pool<T> {
        @Nullable
        T acquire();

        boolean release(@Nullable T instance);
    }


    public static class SimplePool<T> implements Pool<T> {

        private final Object[] mPool;
        private int mPoolSize;


        public SimplePool(int maxPoolSize) {
            if (maxPoolSize <= 0) {
                throw new IllegalArgumentException("maxPoolSize必须>0");
            }
            mPool = new Object[maxPoolSize];
        }

        @Nullable
        @Override
        public T acquire() {
            if (mPoolSize > 0) {
                final int lastPooledIndex = mPoolSize - 1;
                T instance = (T) mPool[lastPooledIndex];
                mPool[lastPooledIndex] = null;
                mPoolSize--;
                return instance;
            }
            return null;
        }

        @Override
        public boolean release(@Nullable T instance) {
            if (isInPool(instance)) {
                throw new IllegalStateException("此对象已经在集合中");
            }
            if (mPoolSize < mPool.length) {
                mPool[mPoolSize] = instance;
                mPoolSize++;
                return true;
            }
            return false;
        }

        private boolean isInPool(@NonNull T instance) {
            for (int i = 0; i < mPoolSize; i++) {
                if (mPool[i] == instance) {
                    return true;
                }
            }
            return false;
        }
    }

    public static class SynchronizedPool<T> extends SimplePool<T> {
        private final Object mLock = new Object();

        public SynchronizedPool(int maxPoolSize) {
            super(maxPoolSize);
        }

        @Nullable
        @Override
        public T acquire() {
            synchronized (mLock) {
                return super.acquire();
            }
        }

        @Override
        public boolean release(@Nullable T instance) {
            synchronized (mLock) {
                return super.release(instance);
            }
        }
    }
}
