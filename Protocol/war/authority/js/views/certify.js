directory.CertifyView = Backbone.View.extend({

	el : '#homeContainer',
	
	initialize : function(options)
	{
		_.bindAll(this);

		this.searchResults = options.searchResults;
        this.productsListView = new directory.ProductListView({model: this.searchResults});
        this.productSummaryView = new directory.ProductSummaryView({collection:this.searchResults});
	},

    render:function () {
        this.$el.html(this.template());
        this.$el.find('.list-container').html(this.productsListView.render().el);
        this.$el.find('.product-container').html(this.productSummaryView.render().el);
        this.delegateEvents();
        return this;
    },

    newProduct:function(){
    	
    	var product = new directory.Product();
    	this.productSummaryView.setModel(product);
    },
    
    renderProduct:function(id){
    	var product = this.searchResults.get(id);
    	if(!product)
    	{
    		Backbone.history.navigate('#', {trigger: true});
    		return;
    	}
    	this.productSummaryView.setModel(product);
    	this.productSummaryView.render();
	},
    
	saveProduct: function()
	{
		this.productSummaryView.updateSaveModel();
	},
	
	// TODO: Must make the merchant name changeable
	certifyProduct: function()
	{
		if(this.productSummaryView.model.id)
		{
			var $cert = this.productSummaryView.$el.find('#cert');
			$.post("/rest/authority/certify/",
				  {
					merchant : "Merchant",
					id : this.productSummaryView.model.id
				  },
				  function(hash,status){
					  $cert.text(hash);
				  }, "text");
		}
	},
	
    showMeBtnClick:function () {
        directory.shellView.search();
    },
    
    events:{
    	'click #saveProduct': 'saveProduct',
    	'click #certifyProduct': 'certifyProduct'
    },

});