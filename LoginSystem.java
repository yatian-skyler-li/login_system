import javax.swing.plaf.synth.SynthOptionPaneUI;

class Entry {
    public String key;
    public int value;
    public int index;

    public Entry(String key, int value) {
        this.key = key;
        this.value = value;
        this.index = -1;
    }
}

class Hashtable {
    public Entry[] hashTable;
    public int size;
    public int count = 0;

    public Hashtable(int size) {
        hashTable = new Entry[size];
        this.size = size;
    }

    public int hashCode(String key) {
        final int CONSTANT = 31;
        int hashKeyValue = 0;
        for (int i = 0; i < key.length(); i++) {
            int charCode = key.charAt(i);
            hashKeyValue += charCode*(Math.pow(CONSTANT,(key.length()-i-1)));
        }
        return hashKeyValue;
    }

    public int compressionFunction(int rawHash) {
        return rawHash % size;
    }

    public void add(String key, String value) {
        Entry entry = new Entry(key, hashCode(value));
        if (count >= size) {
            resizeTable();
        }
        int hashKey = compressionFunction(hashCode(key));
        // linear probing
        if (hashTable[hashKey] != null) {
            for (int i = hashKey; i < size; i++) {
                if (hashTable[i] == null) {
                    hashTable[i] = entry;
                    break;
                }
            }
            for (int i = 0; i < hashKey; i++) {
                if (hashTable[i] == null) {
                    hashTable[i] = entry;
                    break;
                }
            }
        } else {
            hashTable[hashKey] = entry;
        }
        for (int i = 0; i < size; i++) {
            if (hashTable[i] != null) {
                hashTable[i].index = i;
            }
        }
        count++;
    }
    public Entry find(String key) {
        for (Entry entry: hashTable) {
            if (entry != null) {
                if (key.equals(entry.key)) {
                    return entry;
                }
            }
        }
        return null;
    }

    public void remove(String key) {
        for (int i = 0; i < size; i++) {
            if (hashTable[i] != null && hashTable[i].key.equals(key)) {
                hashTable[i] = null;
            }
        }
        count--;
    }

    public void resizeTable() {
        size *= 3;
        Entry[] newTable = new Entry[size];
        for (Entry entry : hashTable) {
            if (entry != null) {
                int newIndex = compressionFunction(hashCode(entry.key));
                newTable[newIndex] = entry;
            }
        }
        hashTable = newTable;
    }
}

public class LoginSystem extends LoginSystemBase {
    final int SIZE = 101;
    Hashtable hashtable = new Hashtable(SIZE);
    @Override
    public int size() {
        /* Add your code here! */
        return hashtable.size;
    }

    @Override
    public int getNumUsers() {
        /* Add your code here! */
        return hashtable.count;
    }

    @Override
    public int hashCode(String key) {
        /* Add your code here! */
        return hashtable.hashCode(key);
    }

    @Override
    public boolean addUser(String email, String password) {
        /* Add your code here! */
        if (hashtable.find(email) == null){
            hashtable.add(email,password);
            return true;
        }
        return false;
    }

    @Override
    public boolean removeUser(String email, String password) {
        /* Add your code here! */
        if (checkPassword(email, password) < 0) {
            return false;
        } else {
            hashtable.remove(email);
            return true;
        }
    }

    @Override
    public int checkPassword(String email, String password) {
        /* Add your code here! */
        if (hashtable.find(email) == null){
            return -1;
        }
        if (hashCode(password) != hashtable.find(email).value){
            return -2;
        } else {
            return hashtable.find(email).index;
        }
    }

    @Override
    public boolean changePassword(String email, String oldPassword, String newPassword) {
        /* Add your code here! */
        if (checkPassword(email,oldPassword) < 0) {
            return false;
        } else {
            removeUser(email, oldPassword);
            addUser(email, newPassword);
            return true;
        }
    }
    /* Add any extra functions below */
}
