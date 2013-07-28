directory.HomeView = Backbone.View.extend({

	el : '#homeContainer',
	
	initialize : function(options)
	{
		_.bindAll(this);

		this.searchResults = options.searchResults;
        this.searchresultsView = new directory.ProductListView({model: this.searchResults});
        this.productSummaryView = new directory.ProductSummaryView({collection:this.searchResults});
	},

    events:{
        'click #saveProduct': 'saveProduct'
    },

    render:function () {
        this.$el.html(this.template());
        this.$el.find('.list-container').html(this.searchresultsView.render().el);
        this.$el.find('.product-container').html(this.productSummaryView.render().el);
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
    		product = new directory.Product({id:id});
    		product.fetch();
		}
    	this.productSummaryView.setModel(product);
    	this.productSummaryView.render();
	},
    
	saveProduct: function()
	{
		this.productSummaryView.updateSaveModel();
	},
	
    showMeBtnClick:function () {
        directory.shellView.search();
    }

});