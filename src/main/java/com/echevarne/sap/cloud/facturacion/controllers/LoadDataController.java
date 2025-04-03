package com.echevarne.sap.cloud.facturacion.controllers;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.echevarne.sap.cloud.facturacion.util.Destinations;
import com.echevarne.sap.cloud.facturacion.util.NewSpan2;

import io.pivotal.cfenv.core.CfApplication;
import io.pivotal.cfenv.core.CfEnv;

//@Slf4j
@RestController
@RequestMapping("/app")
public class LoadDataController {
	static CfApplication APP = new CfEnv().getApp();
	private MemoryMXBean memBean = ManagementFactory.getMemoryMXBean();

    @Autowired
    private Destinations destinations;

    @Autowired
    private Tracer tracer;  // Get Sleuth Tracer bean
    
	@GetMapping("/guid")
	public String getAppGuid() {
		return APP != null ? APP.getApplicationId() : "";
	}

	@GetMapping("/instance-index")
	public String getInstanceIndex() {
		return APP != null ? String.valueOf(APP.getInstanceIndex()) : "-1";
	}
	
	@GetMapping("/instance-guid")
	public String getInstanceGUID() {
		return APP != null ? String.valueOf(APP.getInstanceId()) : "-1";
	}	

	@GetMapping("/used-mem")
	public String getUsedMem() {
		MemoryUsage heap = memBean.getHeapMemoryUsage();
		MemoryUsage nonheap=memBean.getNonHeapMemoryUsage();
		String totmem=(heap != null) && (nonheap !=null)?String.valueOf(heap.getUsed()+nonheap.getUsed()) : "-1";
        Span currentSpan = tracer.currentSpan();
        if (null!=currentSpan) currentSpan.tag("USED_MEM", totmem);		
		return totmem;
	}

    @GetMapping("/lowest-mem/{destination}")
    public String findLessMemLoadedInstance(@PathVariable String destination) {
        Destinations.Enum destinationEnum = Destinations.Enum.valueOf(destination);

        // Check if the destination starts with "Ms"
        if (destinationEnum.getValue().startsWith("Ms")) {
            String headerValue = destinations.getHeaderValueForLessMemoryLoadedInstance(destinationEnum);
            return headerValue != null ? headerValue : "Some error happened";
        } else {
            return "N/A";
        }
    }
    
    @GetMapping("/lowest-mem-parallel/{destination}")
    public String findLessMemLoadedInstanceParallel(@PathVariable String destination) {
        Destinations.Enum destinationEnum = Destinations.Enum.valueOf(destination);

        // Check if the destination starts with "Ms"
        if (destinationEnum.getValue().startsWith("Ms")) {
            String headerValue = destinations.getHeaderValueForLessMemoryLoadedInstanceParallel(destinationEnum);
            return headerValue != null ? headerValue : "Some error happened";
        } else {
            return "N/A";
        }
    }
    
//    @GetMapping("/instance-guids/{destination}")
//    public Map<Integer, String> getInstanceGuidForEachReplica(@PathVariable String destination) {
//        Destinations.Enum destinationEnum = Destinations.Enum.valueOf(destination);
//
//        // Check if the destination starts with "Ms"
//        if (destinationEnum.getValue().startsWith("Ms")) {
//        	return destinations.getInstanceGuidForEachReplica(destinationEnum);
//        } else {
//            return null;
//        }
//    }

    @GetMapping("/instance-guids-parallel/{destination}")
    public Map<Integer, String> getInstanceGuidForEachReplicaParallel(@PathVariable String destination) {
        Destinations.Enum destinationEnum = Destinations.Enum.valueOf(destination);

        // Check if the destination starts with "Ms"
        if (destinationEnum.getValue().startsWith("Ms")) {
        	return destinations.getInstanceGuidForEachReplicaParallel(destinationEnum);
        } else {
            return null;
        }
    }        

    @PostConstruct
    private void init() {
    	this.memBean = ManagementFactory.getMemoryMXBean();
    }
}