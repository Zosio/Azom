package com.gec.zshop.domain;

import com.gec.zshop.utils.SysContants;

public class PageModel {

	//��ǰҳ��
	private int pageIndex;
	//�ܼ�¼����
	private int totalRecordSum;
	//ÿҳ��ʾ��������¼
	private int pageSize;
	//��ҳ��
	private int totalPageSum;
	
	
	public int getPageIndex() {
		
		//��pageIndex<=1����Ĭ�ϵ���1
		this.pageIndex=this.pageIndex<=1?1:this.pageIndex;
		//��pageIndex>=��ҳ��,��Ĭ�ϵ�����ҳ��
		this.pageIndex=this.pageIndex>=getTotalPageSum()?getTotalPageSum():this.pageIndex;
		
		
		return pageIndex;
	}
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}
	public int getTotalRecordSum() {
		return totalRecordSum;
	}
	
	public void setTotalRecordSum(int totalRecordSum) {
		this.totalRecordSum = totalRecordSum;
	}
	public int getPageSize() {
		//����ÿҳ��ʾ��������¼
		this.pageSize=SysContants.DEFAULT_PAGE_SIZE;
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getTotalPageSum() {
		//��ȡ��ҳ��
		this.totalPageSum=(getTotalRecordSum()+getPageSize()-1)/getPageSize();
		return totalPageSum;
	}
	public void setTotalPageSum(int totalPageSum) {
		this.totalPageSum = totalPageSum;
	}
	
	//��ȡ���ݱ��ѯ����ʼλ��
	public int getStartRowNum()
	{
		return (getPageIndex()-1)*getPageSize();
	}

}