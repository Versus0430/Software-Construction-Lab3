package adt;



import static org.junit.Assert.assertEquals;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;



public abstract class MultiIntervalSetTest {

    public abstract MultiIntervalSet<String> emptyInstance();

    /**
     * Testing strategy
     * 閫氳繃涓嶤ollections.emptySet()鏂规硶锛屾祴璇曠┖瀵硅薄鏄惁鍒涘缓鎴愬姛
     */
    @Test
    public void testEmpty(){
        MultiIntervalSet<String> s = emptyInstance();
        assertEquals(Collections.emptySet(),s.labels());
    }

    /**
     * Testing strategy
     * 閫氳繃鍚慚ultiIntervalSet涓紶鍏ヤ竴涓凡缁忓缓绔嬬殑IntervalSet,妫�娴嬫鏃禡ultiIntervalSet鏄惁杩樹负绌�
     */
    @Test
    public void testMultiIntervalSet(){
        MultiIntervalSet<String> s =emptyInstance();
        IntervalSet<String> initial = new CommonIntervalSet<>();
        initial.insert(0,5,"a");
        s.MultiIntervalSet(initial);
        assertEquals(false,s.labels() == null);
    }

    /**
     * Testing strategy
     * 鏍规嵁map涓殑labels涓槸鍚﹀寘鍚柊鐨刲abel杩涜鍒掑垎锛氬寘鍚紝涓嶅寘鍚�
     */
    @Test
    public void testInsert(){
        MultiIntervalSet<String> s =emptyInstance();
        s.insert(0,10,"a");
        assertEquals(true,s.labels().contains("a"));
        s.insert(10,20,"a");
        assertEquals(true,s.intervals("a").labels().contains(1));
    }

    /**
     * Testing strategy
     * 鏍规嵁map涓殑labels涓槸鍚︿负绌鸿繘琛屽垝鍒嗭細绌猴紝涓嶇┖
     */
    @Test
    public void testLabels(){
        MultiIntervalSet<String> s = emptyInstance();
        Set<String> set = new HashSet<>();
        assertEquals(set,s.labels());
        s.insert(0,5,"a");
        set.add("a");
        assertEquals(set,s.labels());
    }

    /**
     * Testing strategy
     * 鏍规嵁map涓殑labels涓槸鍚﹀寘鍚label杩涜鍒掑垎锛氬寘鍚紝涓嶅寘鍚�
     */
    @Test
    public void testRemove(){
        MultiIntervalSet<String> s = emptyInstance();
        assertEquals(false,s.remove("a"));
        s.insert(0,5,"a");
        assertEquals(true,s.remove("a"));
    }

    /**
     * Testing strategy
     * 鏍规嵁杩斿洖鐨勬椂闂存鐨勬暟閲忚繘琛屽垝鍒嗭細0,1,2
     * 娴嬭瘯杩斿洖鐨勬椂闂存鏄惁鏄寜鐓т粠灏忓埌澶х殑椤哄簭鐨勬帓鍒�
     */
    @Test
    public void testIntervals(){
        MultiIntervalSet<String> s = emptyInstance();
        IntervalSet<Integer> t =new CommonIntervalSet<>();
        IntervalSet<Integer> t1 =new CommonIntervalSet<>();
        assertEquals(null,s.intervals("a"));
        s.insert(10,120,"a");
        t.insert(10,120,0);
        assertEquals(t,s.intervals("a"));
        s.insert(5,8,"a");
        t.insert(5,8,1);
        assertEquals(false,s.intervals("a").equals(t));
        t1.insert(5,8,0);
        t1.insert(10,120,1);
        assertEquals(t1,s.intervals("a"));
    }

}