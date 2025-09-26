package com.campus.fitintent;

import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import androidx.databinding.DataBinderMapper;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.campus.fitintent.databinding.ActivityEditProfileBindingImpl;
import com.campus.fitintent.databinding.ActivityNotificationSettingsBindingImpl;
import com.campus.fitintent.databinding.ActivityOnboardingQuizBindingImpl;
import java.lang.IllegalArgumentException;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.RuntimeException;
import java.lang.String;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataBinderMapperImpl extends DataBinderMapper {
  private static final int LAYOUT_ACTIVITYEDITPROFILE = 1;

  private static final int LAYOUT_ACTIVITYNOTIFICATIONSETTINGS = 2;

  private static final int LAYOUT_ACTIVITYONBOARDINGQUIZ = 3;

  private static final SparseIntArray INTERNAL_LAYOUT_ID_LOOKUP = new SparseIntArray(3);

  static {
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.campus.fitintent.R.layout.activity_edit_profile, LAYOUT_ACTIVITYEDITPROFILE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.campus.fitintent.R.layout.activity_notification_settings, LAYOUT_ACTIVITYNOTIFICATIONSETTINGS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.campus.fitintent.R.layout.activity_onboarding_quiz, LAYOUT_ACTIVITYONBOARDINGQUIZ);
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View view, int layoutId) {
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = view.getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
        case  LAYOUT_ACTIVITYEDITPROFILE: {
          if ("layout/activity_edit_profile_0".equals(tag)) {
            return new ActivityEditProfileBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_edit_profile is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYNOTIFICATIONSETTINGS: {
          if ("layout/activity_notification_settings_0".equals(tag)) {
            return new ActivityNotificationSettingsBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_notification_settings is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYONBOARDINGQUIZ: {
          if ("layout/activity_onboarding_quiz_0".equals(tag)) {
            return new ActivityOnboardingQuizBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_onboarding_quiz is invalid. Received: " + tag);
        }
      }
    }
    return null;
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View[] views, int layoutId) {
    if(views == null || views.length == 0) {
      return null;
    }
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = views[0].getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
      }
    }
    return null;
  }

  @Override
  public int getLayoutId(String tag) {
    if (tag == null) {
      return 0;
    }
    Integer tmpVal = InnerLayoutIdLookup.sKeys.get(tag);
    return tmpVal == null ? 0 : tmpVal;
  }

  @Override
  public String convertBrIdToString(int localId) {
    String tmpVal = InnerBrLookup.sKeys.get(localId);
    return tmpVal;
  }

  @Override
  public List<DataBinderMapper> collectDependencies() {
    ArrayList<DataBinderMapper> result = new ArrayList<DataBinderMapper>(1);
    result.add(new androidx.databinding.library.baseAdapters.DataBinderMapperImpl());
    return result;
  }

  private static class InnerBrLookup {
    static final SparseArray<String> sKeys = new SparseArray<String>(1);

    static {
      sKeys.put(0, "_all");
    }
  }

  private static class InnerLayoutIdLookup {
    static final HashMap<String, Integer> sKeys = new HashMap<String, Integer>(3);

    static {
      sKeys.put("layout/activity_edit_profile_0", com.campus.fitintent.R.layout.activity_edit_profile);
      sKeys.put("layout/activity_notification_settings_0", com.campus.fitintent.R.layout.activity_notification_settings);
      sKeys.put("layout/activity_onboarding_quiz_0", com.campus.fitintent.R.layout.activity_onboarding_quiz);
    }
  }
}
