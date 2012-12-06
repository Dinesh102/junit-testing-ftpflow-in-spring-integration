package com.goSmarter.ftp.util;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.goSmarter.ftp.inbound.FTPDownloadGateway;

/**
 * @author Sulekh Bhatia
 * DownloadUtil Class is used to trigger the SI Flow. The FTPDownlaodGateway is autowired into the class.
 */
public class FileDownloadUtil {
	
	private static Logger logger = Logger.getLogger(FileDownloadUtil.class);
	private String remoteftpfolder;
	
	@Autowired
	private FTPDownloadGateway ftpdownloadgateway;
	
	
	/**
	 * @return Download Message Gateway
	 */
	public FTPDownloadGateway getFtpdownloadgateway() {
		return ftpdownloadgateway;
	}
	
	/**
	 * @param Download Message Gateway
	 */
	public void setFtpdownloadgateway(FTPDownloadGateway ftpdownloadgateway) {
		this.ftpdownloadgateway = ftpdownloadgateway;
	}
	
	/**
	 * @return Remote FTP Folder Name
	 */
	public String getRemoteftpfolder() {
		return remoteftpfolder;
	}
	
	/**
	 * @param Set Remote FTP Folder Name
	 */
	public void setRemoteftpfolder(String remoteftpfolder) {
		this.remoteftpfolder = remoteftpfolder;
	}


	/**
	 * This method triggers the SI flow for downloading the files.
	 */
	public void downloadFilesFromRemoteDirectory()
	{
		logger.debug("DOWNLOADING FILES FROM FOLDER "+remoteftpfolder);
		ftpdownloadgateway.downloadFilesFromFTP(remoteftpfolder);
	}
}

