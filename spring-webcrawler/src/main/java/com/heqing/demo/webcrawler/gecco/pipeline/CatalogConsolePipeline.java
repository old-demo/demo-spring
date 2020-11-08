package com.heqing.demo.webcrawler.gecco.pipeline;

import com.geccocrawler.gecco.annotation.PipelineName;
import com.geccocrawler.gecco.pipeline.Pipeline;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.scheduler.DeriveSchedulerContext;
import com.geccocrawler.gecco.spider.HrefBean;
import com.heqing.demo.webcrawler.gecco.model.Catalog;

import java.util.List;

@PipelineName("catalogConsolePipeline")
public class CatalogConsolePipeline implements Pipeline<Catalog> {

    @Override
    public void process(Catalog catalog) {
        List<HrefBean> poemUrlList = catalog.getPoemUrlList();
        for(HrefBean poemUrl : poemUrlList) {
            String url =poemUrl.getUrl();
            System.out.println("请求的详情页 --> " + url);
            HttpRequest currRequest = catalog.getRequest();
            DeriveSchedulerContext.into(currRequest.subRequest(url));
        }
    }
}
