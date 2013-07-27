directory.HomeView = Backbone.View.extend({

	el : '#homeContainer',
	
	initialize : function()
	{
		_.bindAll(this);
        this.searchResults = new directory.ProductCollection();
        this.searchresultsView = new directory.ProductListView({model: this.searchResults});
        this.productSummaryView = new directory.ProductSummaryView({collection:this.searchResults});
        this.searchResults.fetch();
	},

    events:{
        'click #saveProduct': 'saveProduct'
    },

    render:function () {
        this.$el.html(this.template());
        this.$el.find('#productsList').html(this.searchresultsView.render().el);
        this.$el.find('.product-container').html(this.productSummaryView.render().el);
        return this;
    },

    newProduct:function(){
    	var product = new directory.Product();
    	this.productSummaryView.setModel(product);
    },
    
    renderProduct:function(id){
    	var product = this.searchResults.get(id);
    	if(product)
    		this.productSummaryView.setModel(product);
    	else
    		this.newProduct();
	},
    
	saveProduct: function()
	{
		this.productSummaryView.updateSaveModel();
	},
	
    showMeBtnClick:function () {
        directory.shellView.search();
    }

});