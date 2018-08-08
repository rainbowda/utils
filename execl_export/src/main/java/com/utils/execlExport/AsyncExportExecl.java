package com.utils.execlExport;


import com.utils.execlExport.data.BaseParam;

import java.io.OutputStream;
import java.util.List;
import java.util.concurrent.CountDownLatch;
@SuppressWarnings("rawtypes")
public class AsyncExportExecl {
	private ExportExcel exportExcel = new ExportExcel();
	
	public void exportExcel(String sheetName, BaseParam baseParam, OutputStream out) throws Exception {
		List data = baseParam.getData();
		
		int dataLimit = 500000;
		int startIndex = 0,endIndex = dataLimit;
		int sheetNum = data.size()/dataLimit + 1;
		
		CountDownLatch countDownLatch = new CountDownLatch(sheetNum); 
		for (int i = 0; i < sheetNum; i++) {
			if (endIndex > data.size()) {
				endIndex = data.size();
			}
			
			new Thread(new ExeclRunnable(countDownLatch,sheetName+(i+1), baseParam, startIndex, endIndex)).start();;
			
			startIndex += dataLimit;
			endIndex += dataLimit;
		}
		countDownLatch.await();  
		System.out.println("countDownLatch.await() open");
		exportExcel.writeAndDispose(out);
	}
	
	public class ExeclRunnable implements Runnable{
		private CountDownLatch countDownLatch;
		private String sheetName;
		private BaseParam baseParam;
		private Integer startIndex;
		private Integer endIndex;
		
		public ExeclRunnable(CountDownLatch countDownLatch, String sheetName, BaseParam baseParam, Integer startIndex,
				Integer endIndex) {
			this.countDownLatch = countDownLatch;
			this.sheetName = sheetName;
			this.baseParam = baseParam;
			this.startIndex = startIndex;
			this.endIndex = endIndex;
		}



		@Override
		public void run() {
			try {
				exportExcel.createSheet(sheetName, baseParam, startIndex, endIndex);
			} catch (Exception e) {
				System.out.println("导出而execl出现错误");
				e.printStackTrace();
			}
			
			countDownLatch.countDown();
		}
	}
}
