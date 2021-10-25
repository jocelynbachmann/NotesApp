package persistence;

import org.json.JSONObject;

// EFFECTS: returns this as a JSON object
public interface Writable {
    JSONObject toJson();
}

