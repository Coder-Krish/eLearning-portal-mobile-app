package np.com.krishna.nightbeforeexam.HelperClasses;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.File;

public class SharedPrefManager {
    Context context;

    public SharedPrefManager(Context context) {
        this.context = context;
    }

    public void saveLoginDetails(String username, String password) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Username", username);
        editor.putString("Password", password);
        editor.apply();
    }

    public boolean isUserLogedOut() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        boolean isUsernameEmpty = sharedPreferences.getString("Username", "").isEmpty();
        boolean isPasswordEmpty = sharedPreferences.getString("Password", "").isEmpty();
        return isUsernameEmpty || isPasswordEmpty;
    }

    public void saveUserCredentials(String userCredentials) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("userCredentials", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("userCredentials", userCredentials);
        editor.apply();
    }

    public void clearSharedPreferences() {
        File dir = new File(context.getFilesDir().getParent() + "/shared_prefs/");
        String[] children = dir.list();
        for (String child : children) {
            context.getSharedPreferences(child.replace(".xml", ""), Context.MODE_PRIVATE).edit().clear().commit();
            new File(dir, child).delete();
        }
    }

    public String TokenType() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("userCredentials", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("userCredentials", "");
        JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
        String tokenType = jsonObject.get("type").getAsString();
        return tokenType;
    }

    public String getLoggedInUsername() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("userCredentials", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("userCredentials", "");
        JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
        String loggedInUsername = jsonObject.get("username").getAsString();
        return loggedInUsername;
    }
    public String getLoggedInEmail() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("userCredentials", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("userCredentials", "");
        JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
        String loggedInEmail = jsonObject.get("email").getAsString();
        return loggedInEmail;
    }

    public String JwtToken() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("userCredentials", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("userCredentials", "");
        JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
        String token = jsonObject.get("token").getAsString();
        return token;
    }

    public String getRole() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("userCredentials", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("userCredentials", "");
        JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
        String roles = jsonObject.get("roles").getAsString();
        return roles;
    }

    public String Authorization() {
        String token = JwtToken();
        String type = TokenType();
        String authentication = type + " " + token;
        return authentication;
    }

}
