/*
 * Copyright 2017, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.lifecycles.step3_solution;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.android.codelabs.lifecycle.R;

public class ChronoActivity3 extends AppCompatActivity {

    private LiveDataTimerViewModel mLiveDataTimerViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.chrono_activity_3);

        mLiveDataTimerViewModel = ViewModelProviders.of(this).get(LiveDataTimerViewModel.class);

        subscribe();
    }

    private void subscribe() {
        //观察者，在这里刷新UI
        final Observer<Long> elapsedTimeObserver = new Observer<Long>() {
            @Override
            public void onChanged(@Nullable final Long aLong) {
                String newText = ChronoActivity3.this.getResources().getString(
                        R.string.seconds, aLong);
                ((TextView) findViewById(R.id.timer_textview)).setText(newText);
                Log.d("ChronoActivity3", "Updating timer");
            }
        };

        //订阅，使得被观察者持有观察者，被观察者发生数据更新，就调用观察者的onChanged(数据)方法
        //this是声明周期，保证仅在生命周期内有效
        mLiveDataTimerViewModel.getElapsedTime().observe(this, elapsedTimeObserver);
    }
}
