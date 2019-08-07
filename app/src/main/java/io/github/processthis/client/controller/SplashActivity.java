/*  Copyright [2019] [Asher Bearce, Jeffery Franken, Matthew Jones, Jennifer Nevares-Diaz]
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
    http://www.apache.org/licenses/LICENSE-2.0
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
   limitations under the License.
*/

package io.github.processthis.client.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import io.github.processthis.client.R;

/**
 * This class creates a splash screen that will show for 3 seconds upon app start-up
 */
public class SplashActivity extends Activity {

  Handler handler;

  /**
   * On create is a required override that initiates the android life cycle
   * @param savedInstanceState and saved instance data from previous lifecycles
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.splash);

    handler = new Handler();
    handler.postDelayed(new Runnable() {
      /**
       * required method for the runnable class. creates a new thread to time the splash screen
       */
      @Override
      public void run() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
      }
    }, 3000);

  }
}
