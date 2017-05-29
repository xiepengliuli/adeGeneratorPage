package cn.com.infcn.jbzd.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.infcn.ade.core.dao.BaseDaoI;
import cn.com.infcn.core.modelAdd.TtestTree;
import cn.com.infcn.core.pageModelAdd.TestTree;

@Service
public class TestTreeService {

	@Autowired
	private BaseDaoI<TtestTree> testTreeDao;
	

	public List<TestTree> tree(String id) {

		String hql = " from  TtestTree t  where t.ttestTree.id=:id";
		Map<String, Object> params = new HashMap<>();

		if (StringUtils.isBlank(id)) {
			hql = " from TtestTree t where t.ttestTree is null";
		} else {
			params.put("id", id);
		}

		List<TtestTree> testTtreeList = testTreeDao.find(hql, params);

		List<TestTree> testTree = new ArrayList<>();

		for (TtestTree ttestTree2 : testTtreeList) {
			TestTree testTree2 = new TestTree();
			BeanUtils.copyProperties(ttestTree2, testTree2);
			getTreeSub(testTree2);
			testTree.add(testTree2);
		}

		return testTree;
	}

	
	private void getTreeSub(TestTree testTree2) {

		String hql = " from TtestTree t where t.ttestTree.id=:id";
		Map<String, Object> params = new HashMap<>();
		params.put("id", testTree2.getId());

		List<TtestTree> find = testTreeDao.find(hql, params);
		if (find.size() > 0) {
			
			testTree2.setState("open");

			for (TtestTree ttestTree : find) {
				TestTree testTree = new TestTree();
				BeanUtils.copyProperties(ttestTree, testTree);
				getTreeSub(testTree);
				testTree2.getChildren().add(testTree);

			}
			
		}

	}

}
