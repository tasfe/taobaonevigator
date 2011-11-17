package com.payment.taobaoNavigator.dao;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.payment.taobaoNavigator.entity.CatagoryEntity;
import com.payment.taobaoNavigator.entity.ProductEntity;

@Transactional
@Repository
public class NavigatorDaoImpl extends BaseDaoImpl implements NavigatorDao {

	@Override
	public List<CatagoryEntity> findNavigatorByParentId(int pid) {
		Map parameters = new HashMap();
		parameters.put("pid", pid);
		List<CatagoryEntity> childs = this.findByNamedQuery(
				CatagoryEntity.class, CatagoryEntity.FETCH_BY_PARENTID, parameters);
		if (1 == pid) {
			for (Iterator it = childs.iterator(); it.hasNext();) {
				CatagoryEntity child = (CatagoryEntity) it.next();
				if (child.getId() == 1) {
					it.remove();
				}
			}
		}
		return childs;
	}

	@Override
	public CatagoryEntity createNavigator(CatagoryEntity entity) {

		return this.create(entity);
	}

	@Override
	public CatagoryEntity updateNavigator(CatagoryEntity entity) {
		
		return this.update(entity);
	}

	@Override
	public void deleteNavigator(CatagoryEntity entity) {
		
		this.delete(entity);

	}

	@Override
	public CatagoryEntity findNavigatorById(int id) {
		return this.find(CatagoryEntity.class, id);
	}

	@Override
	public Map<CatagoryEntity, List<ProductEntity>> getHotProducts(int pid,int maxNumber) {
		 //get the catagory
			List<CatagoryEntity>Catagorys = getAllChildCatagory( pid);
			Map<CatagoryEntity, List<ProductEntity>> hotProducts=new HashMap<CatagoryEntity, List<ProductEntity>>();
			List<ProductEntity> everyHotProduct=null;
		 //get the product for every catagory	
			for (CatagoryEntity entity : Catagorys) {
				everyHotProduct=this.getHotProduct(entity.getId().toString(), maxNumber);
				hotProducts.put(entity, everyHotProduct);
			}

		return hotProducts;
	}

	@Override
	public List<ProductEntity> getHotProduct(String catagoryId, int maxNumber) {
		
		Map parameters = new HashMap();
		parameters.put("catagoryId", catagoryId);
		List products = this.findByNamedQuery(ProductEntity.class,
				ProductEntity.BYCATAGORYID, parameters,
				Integer.valueOf(maxNumber));

		return products;
	}

	@Override
	public List<CatagoryEntity> getAllChildCatagory(int pid) {

		List<CatagoryEntity> childs = new LinkedList<CatagoryEntity>();
		List<CatagoryEntity> childList = new LinkedList<CatagoryEntity>();

		System.out.println(isNotParentId(pid));
		if (this.isNotParentId(pid)) {
			childs.add(this.findNavigatorById(pid));
		} else {
			if (pid != 1) {
				childs.add(this.findNavigatorById(pid));
			}
			childList = findNavigatorByParentId(pid);
			// add itself and its childList
			for (CatagoryEntity entity : childList) {
				if (entity.getId() == 1)
					continue;
				childs.addAll(getAllChildCatagory(entity.getId()));
			}
		}

		return childs;
	}

	private boolean isNotParentId(int id) {
		List<CatagoryEntity> childCatagorys = this.findNavigatorByParentId(id);
		return (childCatagorys.size() == 0) ? true : false;
	}

}
