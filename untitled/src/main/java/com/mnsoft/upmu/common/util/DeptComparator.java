package com.mnsoft.upmu.common.util;

import java.util.Comparator;

import com.mnsoft.upmu.work.vo.Personal;

public class DeptComparator implements Comparator {

	@Override
	public int compare(Object arg0, Object arg1) {

		String en1 = ((Personal)arg0).getPdept_name();
		String en2 = ((Personal)arg1).getPdept_name();

		return en1.compareTo(en2);
	}

}
