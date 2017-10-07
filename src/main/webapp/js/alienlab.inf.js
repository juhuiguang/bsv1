
/**
 * 发送消息的方法
 * 
 */
(function($, window) {
	$.alienlab={
		postInf:function(config){
			var config_ = {
					mainDate:{
						inf_type:'',
						inf_importance:'',
						inf_content:'',
						inf_post:'',
						inf_get_name:'',
						inf_get:'',
						inf_system:'',
						inf_post:'',
						inf_post_time:'',
						inf_return_url:'',
						inf_term_no:'',	
					},
					returnUrl:'',
			}
			config_=$.extend(true,config_,config);
			this._config = config_;
			var this_ = this;
			if(this.inf_content==null || this.inf_content==''){
				return ;
			}
			this.post =function(){
				var returnInf='';
				function postInfData(){
					$.ajax({
							url: "postInfData.do",
							type: 'POST',
							dataType: 'jsonp',
							data: {
								inf_data:this_._config,
							},
							success: function(rep) {
								if(rep.result == 0){
									returnInf='发送成功';
								}else{
									returnInf='发送失败';
								}
							}
					});
				}
				return returnInf;
			}
		}
	};	
})($,window);
