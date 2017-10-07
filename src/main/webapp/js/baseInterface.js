/**
 * 
 */
(function ($, window) {
	//得到url
	$.getUrl = function(params){
		var params_ = { 
				config:{
					simple_url:true,
					tourl:"",
					fromurl:""
				}
		}; 
		params_ = $.extend(true, params_, params);
		this.config = params_.config;
		var $this = this;
		//url转义特殊符号
		this.URLencode = function(sStr){  
		    return escape(sStr).replace(/\+/g, '%2B').replace(/\"/g,'%22').replace(/\'/g, '%27').replace(/\//g,'%2F');  
		} 
		var url = null;
		//获得token 
		this.getToken = function(){
			$.ajax({ 
				url: 'getToken.do',  
				type: 'POST',   
				dataType: 'json',   
				 async: false,
				data:{ 
					config:JSON.stringify($this.config)
				},  
				success: function(rep) {     
					if(rep.token == null){ 
						//获取密钥失败
						url = null;
					}else{     
						if( rep.config.tourl.indexOf('?') > -1){
							url=  rep.config.tourl+'&flag='+rep.flag+'&config='+encodeURI(JSON.stringify(rep.config))+'&token='+$this.URLencode(rep.token);
						}else{
							url=  rep.config.tourl+'?flag='+rep.flag+'&config='+encodeURI(JSON.stringify(rep.config))+'&token='+$this.URLencode(rep.token);
						}
					} 
				},
				error:function(XMLHttpRequest, textStatus, errorThrown){  
					 alert(XMLHttpRequest.status);
					 alert(XMLHttpRequest.readyState);
					 alert(textStatus);  
				}
				
			});
		}
		$this.getToken();
		return url;
	};
	//请求界面
	$.getJSP = function(params){
		var params_ = { 
				type:"open",
				config:{
					simple_url:true,
					tourl:"",
					fromurl:""
				}
		}; 
		params_ = $.extend(true, params_, params);
		this.config = params_.config;
		this.type = params_.type;
		var $this = this;
		
		//url转义特殊符号
		this.URLencode = function(sStr){  
		    return escape(sStr).replace(/\+/g, '%2B').replace(/\"/g,'%22').replace(/\'/g, '%27').replace(/\//g,'%2F');  
		} 
		//获得token 
		this.getToken = function(){
			$.ajax({ 
				url: 'getToken.do',  
				type: 'POST',   
				dataType: 'json',   
				data:{ 
					config:JSON.stringify($this.config)
				},  
				success: function(rep) { 
					
					if(rep.token == null){ 
						//获取密钥失败
						alert('获取密钥失败');  
					}else{     
						if($this.type == 'location'){  
							console.log(rep.config.tourl.indexOf('?'))
							if( rep.config.tourl.indexOf("?") >= 0){ 
								location.href = rep.config.tourl+'&flag='+rep.flag+'&config='+encodeURI(JSON.stringify(rep.config))+'&token='+$this.URLencode(rep.token);
							}else{
								location.href = rep.config.tourl+'?flag='+rep.flag+'&config='+encodeURI(JSON.stringify(rep.config))+'&token='+$this.URLencode(rep.token);
							}
							 
						}else{ 
							if( rep.config.tourl.indexOf('?') >= 0){
								window.open( (rep.config.tourl+'&flag='+rep.flag+'&config='+encodeURI(JSON.stringify(rep.config))+'&token='+$this.URLencode(rep.token)));
							}else{
								window.open( (rep.config.tourl+'?flag='+rep.flag+'&config='+encodeURI(JSON.stringify(rep.config))+'&token='+$this.URLencode(rep.token)));
							}
							
						}
						
					} 
				},
				error:function(XMLHttpRequest, textStatus, errorThrown){  
					 alert(XMLHttpRequest.status);
					 alert(XMLHttpRequest.readyState);
					 alert(textStatus);  
				}
				
			});
		}
		$this.getToken();
	};
	$.getPort = function(params){
		var params_ = { 
				config:{
					simple_url:true,
					tourl:""
						//其他约定参数
				},
				callback:function(rep){
					console.log(rep) 
				}
		}; 
		params_ = $.extend(true, params_, params);
		this.config = params_.config;
		this.callback = params_.callback;
		var $this = this;
		this.requestPort = function(){
			$.ajax({ 
				url: 'requestPort.do', 
				type: 'POST',    
				dataType: 'json', 
				data:{ 
					config:JSON.stringify($this.config)
				},  
				success: function(rep) {
					$this.callback(rep); 
				},
				error:function(XMLHttpRequest, textStatus, errorThrown){  
					 alert(XMLHttpRequest.status);
					 alert(XMLHttpRequest.readyState);
					 alert(textStatus);  
				}
				
			});
		}
		$this.requestPort();
	}
	
})(jQuery, window);
