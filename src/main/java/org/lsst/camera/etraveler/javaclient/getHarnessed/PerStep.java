package org.lsst.camera.etraveler.javaclient.getHarnessed;

import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;


/**
   Store data associated with one schema generated by one step
*/
public class PerStep {
  private ArrayList<HashMap <String, Object> > m_data=null;

  public static int DT_ABSENT = -2;
  public static int DT_UNKNOWN = -1;
  public static int DT_FLOAT = 0;
  public static int DT_INT = 1;
  public static int DT_STRING = 2;

  public PerStep() {
    m_data = new ArrayList<HashMap <String, Object> >();
    HashMap <String, Object> instance0 = new HashMap<String, Object>();
    instance0.put("schemaInstance", (Integer) 0);
    m_data.add(instance0);
  }
  public void appendInstance(HashMap <String, Object> i) {
    m_data.add(i);
  }

  public int prune(Pair<String, Object> filter, int dtype)
    throws GetHarnessedException {
    String key = filter.getLeft();
    Object val = filter.getRight();

    int valType = dtype;
    if (valType == DT_UNKNOWN) {
      HashMap<String, Object> instance0 = m_data.get(0);
      if (!(instance0.containsKey(key))) return DT_ABSENT;
      String t=(String) instance0.get(key);
      if (t.equals("float") ) {
        valType=DT_FLOAT;
      } else {
        if (t.equals("int")) {
          valType=DT_INT;
        } else {
          if (t.equals("string")) {
            valType=DT_STRING;
          }  else {
            throw new GetHarnessedException("pruneInstances: Unrecognized data type");
          }
        }
      }
    }
    for (int i=(m_data.size() - 1); i > 0; i--) {
      if (!(m_data.get(i).get(key).equals(val)) ) {
        m_data.remove(i);
      }
    }
    return valType;

  }
  public ArrayList<HashMap <String, Object>> getArrayList() {
    return m_data;
  }

}