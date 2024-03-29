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

package io.github.processthis.client;


import android.app.Application;
import com.facebook.stetho.Stetho;
import io.github.processthis.client.service.GoogleSignInService;

/**
 *  Application class extending (@link Application) to create contexts for Stetho, and (@link GoogleSignInService)
 */

public class ProcessThisApplication extends Application {

  public static ProcessThisApplication instance = null;

  @Override
  public void onCreate() {
    super.onCreate();
    instance = this;
    Stetho.initializeWithDefaults(this);

    GoogleSignInService.setContext(this);
  }
}

